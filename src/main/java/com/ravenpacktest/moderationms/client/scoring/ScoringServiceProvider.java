package com.ravenpacktest.moderationms.client.scoring;

public interface ScoringServiceProvider {

    float getOffensivenessScore(String message);
}
