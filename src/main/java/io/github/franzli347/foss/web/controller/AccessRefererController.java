package io.github.franzli347.foss.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FranzLi
 */
@RequestMapping("/accessreferer")
@RestController
@Validated
@Tag(name = "防盗链模块")
public class AccessRefererController {

}
