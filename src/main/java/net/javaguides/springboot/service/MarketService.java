package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;

import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.repository.CityRepository;
import net.javaguides.springboot.repository.MarketRepository;
import net.javaguides.springboot.request.MarketRequest;
import net.javaguides.springboot.request.WorkPlaceRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository repository;
    private final CityRepository cityRepository;


    public MarketRequest createMarket(long city_id,  MarketRequest request) {
        Market market = mapToEntity(request);

        City city = cityRepository.findById(city_id).orElseThrow(() -> new ResourceNotFoundException("City with associated review not found"));

        market.setCity(city);

        Market newMarket = repository.save(market);

        return mapToRequest(newMarket);
    }

    public void save(MarketRequest request){
        var market = Market.builder()
                .market_name(request.getMarket_name())
                .address(request.getAddress())
                .city(request.getCity())
                .build();
        repository.save(market);
    }
    public List<MarketRequest> findAll() {
        List<Market> markets = repository.findAll();
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public List<MarketRequest> getMarketByCity(long id) {

        List<Market> markets = repository.findMarketByCity(id);
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public boolean deleteMarket(long id){
        if(id>=1){
            Market market1 = repository.getById(id);
            if(market1!=null){
                repository.delete(market1);
                return true;
            }
        }
        return false;
    }



    public MarketRequest updateMarket(long marketId, long cityId, MarketRequest request) {
        Market market = repository.findById(marketId).orElseThrow(() -> new ResourceNotFoundException("Market with associated city not found"));

        City city = cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFoundException("City with associate market not found"));

        if(market.getCity().getCity_id() != city.getCity_id()) {
            throw new ResourceNotFoundException("This market does not belong to a city");
        }

        market.setMarket_name(request.getMarket_name());
        market.setAddress(request.getAddress());
        market.setCity(mapToEntity(request).city);
        Market updateMarket = repository.save(market);

        return mapToRequest(updateMarket);
    }


    public MarketRequest getMarketById(long id) {
        Market market = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Market could not be found"));
        return mapToRequest(market);
    }

    private Market mapToEntity(MarketRequest request) {
        Market market = new Market();
        market.setMarket_id(request.getMarket_id());
        market.setMarket_name(request.getMarket_name());
        market.setAddress(request.getAddress());
        market.setCity(request.getCity());
        return market;
    }
    private MarketRequest mapToRequest(Market market) {

        MarketRequest request = new MarketRequest();
        request.setMarket_id(market.getMarket_id());
        request.setMarket_name(market.getMarket_name());
        request.setAddress(market.getAddress());
        request.setCity(market.city);
        return request;
    }
}
