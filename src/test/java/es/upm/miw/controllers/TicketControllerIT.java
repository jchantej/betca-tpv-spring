package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.repositories.core.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TicketControllerIT {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private CashierClosureController cashierClosureController;
    
    @Before
    public void before() {
        this.cashierClosureController.readCashierClosureLast();
    }

    @Test
    public void testCreateTicket() {
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("70"), BigDecimal.ZERO, BigDecimal.ZERO,
                new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), true));
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("8400000000017", "descrip-a1", new BigDecimal("20"), 2, BigDecimal.ZERO, new BigDecimal("20"), false));
        this.ticketController.createTicketAndPdf(ticketCreationInputDto);
        Ticket ticket1 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        this.ticketController.createTicketAndPdf(ticketCreationInputDto);
        Ticket ticket2 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        assertEquals(ticket1.simpleId() + 1, ticket2.simpleId());
        this.ticketRepository.delete(ticket1);
        this.ticketRepository.delete(ticket2);
    }

    @Test
    public void testExistTicket() {
        assertTrue(this.ticketController.existTicket("201801121"));
        assertFalse(this.ticketController.existTicket("201801125"));
    }

    @Test
    public void testGetTicket() {
        Optional<TicketDto> pdf1 = this.ticketController.read("201801121");
        assertTrue(pdf1.isPresent());
        Optional<TicketDto> pdf2 = this.ticketController.read("201801122");
        assertTrue(pdf2.isPresent());
        Optional<TicketDto> pdf3 = this.ticketController.read("201801123");
        assertTrue(pdf3.isPresent());
    }

    @Test
    public void testFindByIdArticleDateBetween() {
        assertNotNull(this.ticketController.findByIdArticleDatesBetween("article1", new Date(), new Date()));
    }

}
