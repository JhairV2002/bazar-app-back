package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.PromoReqDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/promos")
public class PromosController {
    @Autowired
    private PromoService promoService;

    @GetMapping("/get-all")
    public ResponseEntity<List<PromoResDTO>> getAllPromos() {
        return promoService.getAllPromos();
    }

    @PostMapping("/save")
    public ResponseEntity<PromoResDTO> savePromo(PromoReqDTO promoReqDTO) {
        return promoService.savePromo(promoReqDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<PromoResDTO> updatePromo(PromoReqDTO promoReqDTO) {
        return promoService.updatePromo(promoReqDTO);
    }
}
