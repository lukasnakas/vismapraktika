package lt.lukasnakas.controller;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.InvalidIdException;
import lt.lukasnakas.exception.PaymentNotFoundException;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "api/payments")
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@GetMapping
	public ResponseEntity<List<PaymentDTO>> getPayments() {
		return ok(paymentService.getPayments());
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable String id) {
		try {
			return ok(paymentService.getPaymentById(id));
		} catch (InvalidIdException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (PaymentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<PaymentDTO> addPayment(@RequestBody PaymentDTO paymentDTO) {
		try {
			return ok(paymentService.postTransaction(paymentDTO));
		} catch (BadRequestException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (JmsException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
		}
	}

	@GetMapping(value = "{id}/status")
	public ResponseEntity<PaymentDTO> checkPaymentStatus(@PathVariable String id) {
		return ok(paymentService.getPaymentStatus(id));
	}

}
