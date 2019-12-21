package ru.geekbrains;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.repo.CategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CategoriesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testCategoryPostRequest() throws Exception {
        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "-1")
                .param("name", "New category")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories"));

        Category category = new Category();
        category.setName("New category");
        Optional<Category> savedCategory = categoryRepository.findOne(Example.of(category));

        assertTrue(savedCategory.isPresent());
        assertEquals("New category", savedCategory.get().getName());
    }
}
