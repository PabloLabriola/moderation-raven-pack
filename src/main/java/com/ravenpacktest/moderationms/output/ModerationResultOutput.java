package com.ravenpacktest.moderationms.output;

import java.io.InputStream;

public interface ModerationResultOutput {

    InputStream getModerationResultFromCsvFile(InputStream csvFile);
}
