package com.gigaspaces.sbp.metrics.bootstrap.xap;

import org.openspaces.admin.Admin;
import org.openspaces.admin.space.Space;
import org.openspaces.admin.space.SpaceInstance;
import org.openspaces.admin.space.Spaces;
import org.openspaces.admin.transport.Transport;
import org.openspaces.admin.transport.TransportLRMIMonitoring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/15/14
 * Time: 7:26 PM
 * <p/>
 * Connects to the Admin API for an individual Space.
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
class ConnectToSpace implements Runnable{

    private static final String NON_EMPTY_SPACE_NAME_REQUIRED = "A non-empty spaceName is required in order to perform a lookup.";
    private static final String ADMIN_REQUIRED = "Admin required.";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String spaceName;
    private final Admin admin;

    public ConnectToSpace(String spaceName, Admin admin) {
        assert spaceName != null && spaceName.trim().length() > 0 : NON_EMPTY_SPACE_NAME_REQUIRED;
        assert admin != null : ADMIN_REQUIRED;
        this.spaceName = spaceName;
        this.admin = admin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

        logger.debug(String.format("Waiting indefinitely to connect to XAP Space with name '%s'.", spaceName));
        long start = System.currentTimeMillis();
        Spaces spaces = admin.getSpaces();
        Space space = spaces.waitFor(spaceName);
        long stop = System.currentTimeMillis();
        logger.debug(String.format("Successfully connected to XAP Space with name '%s' in %d milliseconds.", spaceName, (stop - start)));

        for (SpaceInstance spaceInstance : space.getInstances()) {
            Transport transport = spaceInstance.getTransport();
            TransportLRMIMonitoring lrmiMonitoring = transport.getLRMIMonitoring();
            if( lrmiMonitoring != null ) {
                logger.debug(String.format("Enabling LRMI monitoring for spaceInstance %s with backupId %s.", spaceInstance.getInstanceId(), spaceInstance.getBackupId()));
                lrmiMonitoring.enableMonitoring();
                logger.debug(String.format("LRMI monitoring successfully enabled for spaceInstance %s with backupId %s.", spaceInstance.getInstanceId(), spaceInstance.getBackupId()));
            }
        }

    }

}
