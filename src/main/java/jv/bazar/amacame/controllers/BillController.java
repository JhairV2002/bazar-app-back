package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.services.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private static final Logger log = LoggerFactory.getLogger(BillController.class);
    @Autowired
    private BillService billService;

    @GetMapping("/get-all/")
    public GenericResponseDTO<List<BillResDTO>> listAll() {
        return GenericResponseDTO.<List<BillResDTO>>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .data(billService.getBills())
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping(value = "/create/", consumes = "application/json")
    public GenericResponseDTO<BillResDTO> createBill(@RequestBody BillReqDTO billReqDTO) {
        log.info("Creating bill: {}");
        return GenericResponseDTO.<BillResDTO>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .data(billService.createBill(billReqDTO))
                .code(HttpStatus.OK.value())
                .build();
    }
}
