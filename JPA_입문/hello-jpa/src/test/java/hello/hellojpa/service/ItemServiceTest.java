package hello.hellojpa.service;

import hello.hellojpa.domain.item.Album;
import hello.hellojpa.domain.item.Item;
import hello.hellojpa.exception.NotEnoughStockException;
import hello.hellojpa.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품저장() throws Exception {
        //given
        Album album = new Album();
        album.setArtist("kim");
        album.setEtc("etc");
        itemService.saveItem(album);

        //when
        Item result = itemService.findOne(album.getId());

        //then
        Assertions.assertThat(album.getId()).isEqualTo(result.getId());
    }

    @Test
    public void 전체조회() throws Exception {
        //given
        Album album1 = new Album();
        Album album2 = new Album();
        album1.setArtist("kim1");
        album2.setArtist("kim2");
        itemService.saveItem(album1);
        itemService.saveItem(album2);

        //when
        List<Item> result = itemService.findItems();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 단일조회() throws Exception {
        //given
        Album album1 = new Album();
        Album album2 = new Album();
        album1.setArtist("kim1");
        album2.setArtist("kim2");
        itemService.saveItem(album1);
        itemService.saveItem(album2);

        //when
        Item result = itemService.findOne(album1.getId());

        //then
        Assertions.assertThat(result.getId()).isEqualTo(album1.getId());
    }

    @Test
    public void 수량추가() throws Exception {
        //given
        Album album1 = new Album();
        album1.setStockQuantity(100);

        //when
        album1.addStock(10);

        //then
        Assertions.assertThat(album1.getStockQuantity()).isEqualTo(110);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 수량감소() throws Exception {
        //given
        Album album1 = new Album();
        album1.setStockQuantity(100);

        //when
        album1.removeStock(110);

        //then
        fail("예외가 발생해야 한다.");
    }

}