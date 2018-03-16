package es.upm.miw.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.BudgetCreationInputDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.repositories.core.BudgetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class BudgetControllerIT {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetController budgetController;

    @Test
    public void testCreateBudget() {
        List<ShoppingDto> shoppingList = new ArrayList<ShoppingDto>();
        shoppingList.add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), false));
        BudgetCreationInputDto budgetCreationDto = new BudgetCreationInputDto(shoppingList);
        int totalBudgets = this.budgetRepository.findAll().size();
        
        this.budgetController.createBudget(budgetCreationDto);
        assertEquals(totalBudgets + 1, this.budgetRepository.findAll().size());       
    }

}
