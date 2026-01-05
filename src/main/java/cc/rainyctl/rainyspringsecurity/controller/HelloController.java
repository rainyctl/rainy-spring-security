package cc.rainyctl.rainyspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Security!";
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('test')")
    public String hello2() {
        return "ok, you have test authority";
    }

    @GetMapping("/hello3")
    @PreAuthorize("hasAuthority('sys:book:delete')")
    public String hello3() {
        return "ok, you have sys:book:delete";
    }

    @GetMapping("/hello4")
    @PreAuthorize("@cer.hasModuleAuthority('sys:*')")
    public String hello4() {
        return "ok, you have sys:*";
    }
}
