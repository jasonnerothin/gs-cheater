package com.gigaspaces.sbp.metrics;

import akka.actor.ActorSystem;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 12/11/14
 * Time: 2:32 PM
 */
// NOTE: This code was copied out of this codebase: https://github.com/GigaSpaces-ProfessionalServices/AdminApiMonitor
// and should be replaced once that code is built out to cloudbees or some other publicly-available
// repository.
@Deprecated
@Component
public class ActorSystemEden {

    private final ActorSystem actorSystem = ActorSystem.create("mySystem");

    public ActorSystem getSystem(){
        return actorSystem;
    }

}
