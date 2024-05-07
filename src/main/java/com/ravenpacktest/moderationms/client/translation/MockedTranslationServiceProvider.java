package com.ravenpacktest.moderationms.client.translation;

import org.springframework.stereotype.Service;

@Service
public class MockedTranslationServiceProvider implements TranslationServiceProvider {

    @Override
    public String getTranslatedMessage(final String message) {
        return message;
    }
}
