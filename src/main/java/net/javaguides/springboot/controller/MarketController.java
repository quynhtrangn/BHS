package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;import net.javaguides.springboot.request.MarketRequest;
import net.javaguides.springboot.service.MarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth/markets")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> marketDetail(@PathVariable long id) {
        return ResponseEntity.ok(service.getMarketById(id));
    }

    @PostMapping("/Market/{city_id}/city")
    public ResponseEntity<MarketRequest> createMarket(@PathVariable(value = "city_id") long city_id, @RequestBody MarketRequest marketRequest) {
        return new ResponseEntity<>(service.createMarket(city_id, marketRequest), HttpStatus.CREATED);
    }

//    @PostMapping("/addMarket")
//    public ResponseEntity<?> save(
//            @RequestBody MarketRequest request
//    ) {
//        service.save(request);
//        return ResponseEntity.accepted().build();
//    }

    @GetMapping("/getAllMarkets")
    public List<MarketRequest> findAllMarkets() {
        return service.findAll();
    }

    @GetMapping("/getMarketByCity/{id}")
    public List<MarketRequest> findMarketByCity(
            @PathVariable("id")long city_id
    ) {

        return  service.getMarketByCity(city_id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMarket(@PathVariable("id") long market_id) {
        service.deleteMarket(market_id);
        return new ResponseEntity<>("Market delete", HttpStatus.OK);
    }



    @PutMapping("/{market_id}/city/{city_id}")
    public ResponseEntity<?> updateMarket(@PathVariable(value = "market_id") long market_id, @PathVariable(value = "city_id") long city_id,
                                                  @RequestBody MarketRequest request) {
        MarketRequest updatedMarket = service.updateMarket(market_id,city_id,request);
        return new ResponseEntity<>(updatedMarket, HttpStatus.OK);
    }

}
