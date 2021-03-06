package es.upm.miw.resources;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.upm.miw.controllers.CashierClosureController;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.ClosedCashierOutputDto;
import es.upm.miw.dtos.CashierClosingOutputDto;
import es.upm.miw.dtos.CashierMovementInputDto;
import es.upm.miw.resources.exceptions.CashierException;
import es.upm.miw.resources.exceptions.NotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CashierClosureResource.CASHIER_CLOSURES)
public class CashierClosureResource {

    public static final String CASHIER_CLOSURES = "/cashier-closures";

    public static final String LAST = "/last";

    public static final String MOVEMENTS = "/movements";

    public static final String TOTALS = "/totals";

    public static final String SEARCH = "/search";

    public static final String DATE = "/date";

    @Autowired
    private CashierClosureController cashierClosureController;

    @PostMapping
    public void openCashierClosure() throws CashierException {
        cashierClosureController.createCashierClosure();
    }

    @PostMapping(value = LAST + MOVEMENTS)
    public void createCashMovement(@Valid @RequestBody CashierMovementInputDto cashierMovementDto)
            throws CashierException, NotFoundException {
        this.cashierClosureController.createCashierMovement(cashierMovementDto);
    }

    @GetMapping(value = LAST)
    public CashierLastOutputDto getCashierClosureLast() {
        return cashierClosureController.readCashierClosureLast();
    }

    @GetMapping(value = LAST + TOTALS)
    public CashierClosingOutputDto readTotalsFromLast() throws CashierException {
        return this.cashierClosureController.readTotalsFromLast();
    }

    @PatchMapping(value = LAST)
    public void closeCashierClosure(@Valid @RequestBody CashierClosureInputDto cashierClosureDto) throws CashierException {
        cashierClosureController.close(cashierClosureDto);
    }

    @GetMapping(value = SEARCH + DATE)
    public List<ClosedCashierOutputDto> findBetweenDate(@RequestParam long start, @RequestParam long end) {
        return this.cashierClosureController.findBetweenDate(new Date(start), new Date(end));
    }

}
