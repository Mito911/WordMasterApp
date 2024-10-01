package pl.maciejtuznik.WordMasterApp.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordsRepository extends JpaRepository<Words,Integer> {

    @Query(value="SELECT * FROM Words ORDER BY RAND() LIMIT :count",nativeQuery = true)
    List<Words> findRandomWords(@Param("count")int count);

    @Query(value = "SELECT * FROM Words WHERE created_at>=NOW()-INTERVAL 1 DAY",nativeQuery = true)
    List<Words> findWordsFromLastDay();

    @Query(value="SELECT * FROM Words WHERE created_at >= NOW()-INTERVAL 1 WEEK ", nativeQuery = true)
    List<Words> findWordsFromLastWeek();

    @Query(value = "SELECT * FROM Words WHERE created_at >=NOW()-INTERVAL 1 MONTH", nativeQuery = true)
    List<Words> findWordsFromLastMonth();

    List<Words> findByCategoryId(Integer categoryId);


}
