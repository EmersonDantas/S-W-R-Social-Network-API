package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Genre;
import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class NotifyAllRebelsAsyncServiceImpl implements NotifyAllRebelsAsyncService {

    @Async("taskExecutorA")
    @Override
    public void notifyAllRebels(String message) {
        // Simulate method execution time
        List<Rebel> rebels = new ArrayList<>();

        long pause = 600000;
        try {
            log.info("A - sending notifications. Priority: {}", Thread.currentThread().getPriority());
            for(int i = 0; i < 600; i++){
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1956-10-21");
                rebels.add(Rebel.builder()
                        .name("Leia Organa")
                        .dateOfBirth(date)
                        .genre(Genre.toEnum("femi"))
                        .galaxy("Via Láctea")
                        .base("Echo")
                        .location(new Location(0.0,0.0,"base"))
                        .items(new ArrayList<>(Arrays.asList(Item.builder().name("comida").amount(6).points(1).build())))
                        .denunciations(0).build());
            }
            Thread.sleep(pause);
            log.info("B - successfully notified");
        } catch (Exception e) {
            log.error("Exception on notify all rebels");
            e.printStackTrace();
        }
    }
}
