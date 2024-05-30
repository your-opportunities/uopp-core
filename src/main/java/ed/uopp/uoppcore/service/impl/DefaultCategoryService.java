package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.repository.CategoryRepository;
import ed.uopp.uoppcore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class DefaultCategoryService implements CategoryService {

    public static final String NO_CATEGORY_WITH_NAME_FOUND = "No category with name '%s' found";
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByName(String categoryName) {
        requireNonNull(categoryName);

        return categoryRepository.findByName(categoryName).orElseThrow(
                () -> new NoSuchElementException(String.format(NO_CATEGORY_WITH_NAME_FOUND, categoryName))
        );
    }
}
