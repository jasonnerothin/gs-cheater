package com.gigaspaces;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 12/16/14
 * Time: 10:41 PM
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
public interface Factory<T> {

    T build();

}
