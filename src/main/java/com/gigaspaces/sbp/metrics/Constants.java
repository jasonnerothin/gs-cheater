package com.gigaspaces.sbp.metrics;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 9/5/14
 * Time: 7:19 PM
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
public interface Constants {

    String DATE_FORMAT = "dd-M-yyyy HH:mm:ss";
    String DEFAULT_FLAG_VALUE = Boolean.TRUE.toString();
    String LIST_ITEM_SEPARATOR = ",";
    String THING_REQUIRED = "A %s is required.";
    String THINGS_REQUIRED = "%s are required.";

    String ALERTS_EMAIL_ADDRESS_SYSTEM_PROPERTY_NAME = "alertsEmailAddress";
    String OUTPUT_FILE_SYSTEM_PROPERTY_NAME = "outputFile";

}
