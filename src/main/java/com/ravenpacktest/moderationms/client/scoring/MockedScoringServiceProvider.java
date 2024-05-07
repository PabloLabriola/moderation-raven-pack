package com.ravenpacktest.moderationms.client.scoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MockedScoringServiceProvider implements ScoringServiceProvider {

    private final Random random;

    @Override
    public float getOffensivenessScore(final String message) {
        return random.nextFloat();
    }
}
