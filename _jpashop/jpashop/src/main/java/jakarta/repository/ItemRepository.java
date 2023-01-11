package jakarta.repository;

import jakarta.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        // 아이템이 처음에 없으면 그냥 jpa가 제공해주는 것 쓴다.
        if(item.getId() == null){
                em.persist(item);
        }else{ // 이미 등록된걸 가져온거 업데이트
            em.merge(item); // 강제 업데이트 같은 역할이다.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
