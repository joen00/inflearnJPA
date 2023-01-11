package jakarta.service;

import jakarta.domain.item.Book;
import jakarta.domain.item.Item;
import jakarta.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // 리드온리면 저장이 안된다.
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // 변경감지를 해야한다. 따라서 변경해야할 것 들만 데리고 Set를 해야한다.
    // 아니면 변경 못한 값은 null값으로 간다.
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){ // UpdateItemDto itemDto => String name, int price, int stockQuantity
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
    // merge가 이 방식이다.


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
