package com.gigaspaces.sbp.metrics.bootstrap.cli;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/13/14
 * Time: 8:42 PM
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
public enum OutputFormat {

    Csv
    , LogFormat
    , Carbon // FFR
    , InfluxDb // FFR
    ;

}
