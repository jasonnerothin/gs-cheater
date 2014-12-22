package com.gigaspaces.sbp.metrics.bootstrap.xap;

import com.gigaspaces.Factory;
import org.openspaces.admin.AdminFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/18/14
 * Time: 3:08 PM
 *
 * Provides a means of testing with DI.
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
@Component
class AdminFactoryFactory implements Factory<AdminFactory> {

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminFactory build(){
        return new AdminFactory();
    }

}
