package br.com.psf.personalsystemfinance.rest;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/installment")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class InstallmentController {
}
