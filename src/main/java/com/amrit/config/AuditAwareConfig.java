package com.amrit.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		return Optional.of(2);
	}

}
