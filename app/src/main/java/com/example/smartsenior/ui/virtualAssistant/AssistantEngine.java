package com.example.smartsenior.ui.virtualAssistant;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.*;

public class AssistantEngine {

    private final List<QAEntry> entries = new ArrayList<>();

    private static final Set<String> STOP = new HashSet<>(Arrays.asList(
            "i","a","oraz","ze","że","to","jest","w","we","na","do","od","z","za",
            "czy","jak","co","kiedy","gdzie","dlaczego","sie","się","nie","tak",
            "dla","u","o","po","pod","nad","przy","ale","mi","mnie","moj","moja"
    ));

    public AssistantEngine(Context ctx, String assetFileName) throws Exception {
        String json = readAsset(ctx, assetFileName);
        JSONArray arr = new JSONArray(json);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);

            String q = o.optString("question", "");
            String a = o.optString("answer", "");

            JSONArray kwArr = o.optJSONArray("keywords");
            List<String> keywords = new ArrayList<>();
            if (kwArr != null) {
                for (int k = 0; k < kwArr.length(); k++) {
                    keywords.add(normalize(kwArr.optString(k, "")));
                }
            }

            String qNorm = normalize(q);

            entries.add(new QAEntry(
                    q,
                    a,
                    qNorm,
                    tokenizeToSet(qNorm),
                    keywords
            ));
        }
    }

    public String reply(String userText) {
        String uNorm = normalize(userText);
        if (uNorm.isBlank()) return "Napisz proszę pytanie trochę dokładniej.";

        Set<String> uTokens = tokenizeToSet(uNorm);
        if (uTokens.isEmpty()) return "Napisz proszę pytanie trochę dokładniej.";

        double bestScore = 0.0;
        QAEntry best = null;

        for (QAEntry e : entries) {
            double score = score(uNorm, uTokens, e);
            if (score > bestScore) {
                bestScore = score;
                best = e;
            }
        }

        if (best == null || bestScore < 0.55) {
            return "Nie jestem pewien. Spróbuj użyć innych słów (np. „BLIK”, „wnuczek”, „bank”, „SMS”, „link”).";
        }

        return best.answer;
    }

    private double score(String uNorm, Set<String> uTokens, QAEntry e) {
        double score = 0.0;

        // 1) keywords
        int kwHits = 0;
        for (String kw : e.keywordsNorm) {
            if (kw.isBlank()) continue;

            if (kw.contains(" ")) {
                if (uNorm.contains(kw)) kwHits++;
            } else {
                if (uTokens.contains(kw)) kwHits++;
            }
        }
        score += Math.min(0.75, kwHits * 0.22);

        // 2) overlap słów z pytaniem bazowym
        int common = 0;
        for (String t : uTokens) {
            if (e.qTokens.contains(t)) common++;
        }
        double overlap = (uTokens.size() == 0) ? 0.0 : (double) common / (double) uTokens.size();
        score += overlap * 0.45;

        // 3) bonus frazy
        if (uNorm.contains(e.questionNorm)) score += 0.35;

        return Math.min(1.0, score);
    }

    private static Set<String> tokenizeToSet(String normalized) {
        String[] parts = normalized.split("\\s+");
        Set<String> out = new LinkedHashSet<>();
        for (String p : parts) {
            if (p.isBlank()) continue;
            if (p.length() < 2) continue;
            if (STOP.contains(p)) continue;
            out.add(p);
        }
        return out;
    }

    private static String normalize(String s) {
        String lower = (s == null ? "" : s).toLowerCase(Locale.ROOT).trim();
        String noDia = Normalizer.normalize(lower, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return noDia.replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private static String readAsset(Context ctx, String filename) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(ctx.getAssets().open(filename)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}
