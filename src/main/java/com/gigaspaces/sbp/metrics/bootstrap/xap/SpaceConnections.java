package com.gigaspaces.sbp.metrics.bootstrap.xap;

import org.openspaces.admin.Admin;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/18/14
 * Time: 2:19 PM
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
@Component
class SpaceConnections {

    ConnectToSpace connect(String spaceName, Admin admin){
        return new ConnectToSpace(spaceName, admin);
    }
}
