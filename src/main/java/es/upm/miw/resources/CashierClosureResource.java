package es.upm.miw.resources;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.upm.miw.controllers.CashierClosureController;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierClosureLastOutputDto;
import es.upm.miw.dtos.CashierClosureSearchOutputDto;
import es.upm.miw.resources.exceptions.CashierClosedException;
import es.upm.miw.resources.exceptions.CashierCreateException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CashierClosureResource.CASHIER_CLOSURES)
public class CashierClosureResource {

	public static final String CASHIER_CLOSURES = "/cashier-closures";

	public static final String LAST = "/last";

	public static final String SEARCH = "/search";

	public static final String GET_TOTALS = "/get-totals";

	@Autowired
	private CashierClosureController cashierClosureController;

	@GetMapping(value = LAST)
	public CashierClosureLastOutputDto getCashierClosureLast() {
		return cashierClosureController.getCashierClosureLast();
	}

	@PostMapping
	public void createCashierClosure() throws CashierCreateException {
		Optional<String> error = cashierClosureController.createCashierClosure();
		if (error.isPresent()) {
			throw new CashierCreateException(error.get());
		}
	}

	@PatchMapping(value = LAST)
	public void closeCashierClosure(@Valid @RequestBody CashierClosureInputDto cashierClosureDto)
			throws CashierClosedException {
		Optional<String> error = cashierClosureController.close(cashierClosureDto);
		if (error.isPresent()) {
			throw new CashierClosedException(error.get());
		}
	}

	@RequestMapping(value = SEARCH, method = RequestMethod.GET)
	public List<CashierClosureSearchOutputDto> findAllSalesCashierClosure(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("dateStart") Date dateStart,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "dateFinish") Date dateFinish) {
		return this.cashierClosureController.getAllSalesCashierClosure(dateStart, dateFinish);
	}

	@RequestMapping(value = GET_TOTALS, method = RequestMethod.GET)
	public CashierClosureSearchOutputDto getTotalCardAndCash() {
		return this.cashierClosureController.getTotalCardAndCash();
	}

}
