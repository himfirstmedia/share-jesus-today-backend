package com.himfirst.vidshare.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		// Add custom mappings for other model-entity pairs
		// ...

		return modelMapper;
	}
}