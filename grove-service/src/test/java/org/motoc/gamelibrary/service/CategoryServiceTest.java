package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.repository.fragment.CategoryFragmentRepository;
import org.motoc.gamelibrary.repository.jpa.CategoryRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository repository;

    @Mock
    CategoryFragmentRepository repositoryCustom;

    @InjectMocks
    CategoryService service;

    // Following tests have been commented. It could help writing Game's tests

//    @Test
//    void edit_ShouldCreate() {
//        final long parentId = 5L;
//        final String parentName = "Réfléxion";
//        final long id = 1L;
//        final String name = "Stratégie";
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle(parentName);
//
//        Category category = new Category();
//        category.setId(id);
//        category.setTitle(name);
//        category.setParent(parent);
//
//        parent.getChildren().add(category);
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//        when(repository.save(category)).thenReturn(category);
//
//        assertThat(service.edit(category, id)).isEqualTo(category);
//
//    }
//
//    @Test
//    void edit_ShouldReplace() {
//        final long parentId = 5L;
//        final String parentName = "Réfléxion";
//        final long id = 1L;
//        final String name = "Stratégie";
//        final String nameToBeReplaced = "Straéigte";
//
//        Category toBeReplaced = new Category();
//        toBeReplaced.setId(id);
//        toBeReplaced.setTitle(nameToBeReplaced);
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle(parentName);
//
//        Category category = new Category();
//        category.setId(id);
//        category.setTitle(name);
//        category.setParent(parent);
//
//        parent.getChildren().add(category);
//
//        when(repository.findById(id)).thenReturn(Optional.of(toBeReplaced));
//        when(repository.save(toBeReplaced)).thenReturn(category);
//
//        assertThat(service.edit(category, id)).isEqualTo(category);
//    }

//    @Test
//    void addChildren_shouldThrow_NotFoundException() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L, 2L, 3L);
//
//        when(repository.findById(catId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> service.addChildren(childrenIds, catId)).hasMessageContaining("No category of id=" + catId + " found.");
//
//    }
//
//    @Test
//    void addChildren_shouldThrow_ChildAndParentException1() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L);
//
//        Category child = new Category();
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//        category.setParent(child);
//
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//
//        assertThatThrownBy(() -> service.addChildren(childrenIds, catId))
//                .hasMessageContaining("The parent category : " + category
//                        + " has already a parent : " + category.getParent());
//    }
//
//    @Test
//    void addChildren_shouldThrow_ChildAndParentException2() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L);
//
//        Category childParent = new Category();
//
//        Category child = new Category();
//        child.setId(1L);
//        child.setTitle("Refléxion");
//        child.setParent(childParent);
//
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        List<Category> children = Arrays.asList(child);
//
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//        when(repository.findAllById(childrenIds)).thenReturn(children);
//
//        assertThatThrownBy(() -> service.addChildren(childrenIds, catId))
//                .hasMessageContaining("The candidate child : " + child
//                        + " has already a parent  : " + child.getParent());
//    }
//
//    @Test
//    void addChildren_shouldThrow_ChildAndParentException3() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L);
//
//        Category childChild = new Category();
//
//        Category child = new Category();
//        child.setId(1L);
//        child.setTitle("Refléxion");
//        child.getChildren().add(childChild);
//
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        List<Category> children = Arrays.asList(child);
//
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//        when(repository.findAllById(childrenIds)).thenReturn(children);
//
//        assertThatThrownBy(() -> service.addChildren(childrenIds, catId))
//                .hasMessageContaining("The candidate child : " + child
//                        + " has already at least one child : children.size()=" + child.getChildren().size());
//    }
//
//    @Test
//    void addChildren_shouldThrow_IllegalArgumentException() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L);
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//        when(repository.findAllById(childrenIds)).thenThrow(new IllegalArgumentException("message"));
//        assertThatThrownBy(() -> service.addChildren(childrenIds, catId))
//                .hasMessageContaining("message");
//    }
//
//    @Test
//    void addChildren() {
//        final long catId = 5L;
//        List<Long> childrenIds = Arrays.asList(1L);
//
//        Category child = new Category();
//        child.setId(1L);
//        child.setTitle("Refléxion");
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        List<Category> children = Arrays.asList(child);
//
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//        when(repository.findAllById(childrenIds)).thenReturn(children);
//
//        Category toReturn = new Category();
//        toReturn.setId(category.getId());
//        toReturn.setTitle(category.getTitle());
//        toReturn.getChildren().add(child);
//
//        when(repositoryCustom.saveWithChildren(children, category)).thenReturn(toReturn);
//        assertThat(service.addChildren(childrenIds, catId)).isEqualTo(toReturn);
//    }
//
//    @Test
//    void addParent_shouldThrow_NotFoundException() {
//        final long catId = 5L;
//        final long parentId = 1L;
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle("Refléxion");
//
//
//        when(repository.findById(parentId)).thenReturn(Optional.empty());
//
//
//        assertThatThrownBy(() -> service.addParent(parentId, catId)).hasMessageContaining("No parent category of id=" + parentId + " found.");
//    }
//
//
//    @Test
//    void addParent_shouldThrow_ChildAndParentException_1() {
//        final long catId = 5L;
//        final long parentId = 1L;
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle("Refléxion");
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//
//        when(repository.findById(parentId)).thenReturn(Optional.of(parent));
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//
//        parent.getChildren().add(category);
//
//        assertThatThrownBy(() -> service.addParent(parentId, catId)).hasMessageContaining("Category : " + parent.getTitle() + " is already the parent of " + category.getTitle());
//
//    }
//
//    @Test
//    void addParent_shouldThrow_ChildAndParentException_2() {
//        final long catId = 5L;
//        final long parentId = 1L;
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle("Refléxion");
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        when(repository.findById(parentId)).thenReturn(Optional.of(parent));
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//
//        category.setParent(new Category());
//
//        assertThatThrownBy(() -> service.addParent(parentId, catId)).hasMessageContaining("The category of id=" + catId + " already has a parent");
//
//    }
//
//    @Test
//    void addParent_shouldThrow_ChildAndParentException_3() {
//        final long catId = 5L;
//        final long parentId = 1L;
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle("Refléxion");
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        when(repository.findById(parentId)).thenReturn(Optional.of(parent));
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//
//        category.getChildren().add(parent);
//
//        assertThatThrownBy(() -> service.addParent(parentId, catId)).hasMessageContaining("Category " + category.getTitle() + " has at least one child : it can't have a parent");
//    }
//
//    @Test
//    void addParent() {
//        final long catId = 5L;
//        final long parentId = 1L;
//
//        Category parent = new Category();
//        parent.setId(parentId);
//        parent.setTitle("Refléxion");
//
//        Category category = new Category();
//        category.setId(catId);
//        category.setTitle("Stratégie");
//
//        when(repository.findById(parentId)).thenReturn(Optional.of(parent));
//        when(repository.findById(catId)).thenReturn(Optional.of(category));
//
//        Category toReturn = new Category();
//        toReturn.setId(category.getId());
//        toReturn.setTitle(category.getTitle());
//        toReturn.setParent(parent);
//
//        when(repositoryCustom.saveWithParent(parent, category)).thenReturn(toReturn);
//
//        assertThat(service.addParent(parentId, catId)).isEqualTo(toReturn);
//    }
}