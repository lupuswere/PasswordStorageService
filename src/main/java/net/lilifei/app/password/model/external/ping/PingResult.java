package net.lilifei.app.password.model.external.ping;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PingResult {

    private boolean healthy;
}
