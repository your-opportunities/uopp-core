package ed.uopp.uoppcore.repository;

import ed.uopp.uoppcore.entity.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {

    Optional<Format> findByName(String formatName);

}
