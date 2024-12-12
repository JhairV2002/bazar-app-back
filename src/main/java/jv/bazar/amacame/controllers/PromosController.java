package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.PromoReqDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promos")
public class PromosController {
    @Autowired
    private PromoService promoService;

    @GetMapping("/get-all")
    public GenericResponseDTO<List<PromoResDTO>> getAllPromos() {
        return promoService.getAllPromos();
    }

    @PostMapping("/save")
    public GenericResponseDTO<PromoResDTO> savePromo(@RequestBody PromoReqDTO promoReqDTO) {
        return promoService.createPromo(promoReqDTO);
    }

    @PutMapping("/update")
    public GenericResponseDTO<PromoResDTO> updatePromo(@RequestBody PromoReqDTO  promoReqDTO) {
        return promoService.updatePromo(promoReqDTO);
    }
}
