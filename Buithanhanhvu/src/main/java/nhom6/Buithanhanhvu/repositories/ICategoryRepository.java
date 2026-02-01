package nhom6.Buithanhanhvu.repositories;

import nhom6.Buithanhanhvu.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
} 