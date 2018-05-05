package com.youtube.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TemporarytestBullshit {

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<String> doStuff(@RequestParam("path") String path) throws Exception {
		final Stream<Path> files = Files.list(Paths.get(path));
		final List<String> paths = files.map(p -> p.toAbsolutePath().toString()).collect(Collectors.toList());
		files.close();
		return paths;
	}

}
