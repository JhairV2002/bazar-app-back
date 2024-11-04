package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.entity.Bill;
import jv.bazar.amacame.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@CrossOrigin(origins = "http://localhost:3000")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/get-all/")
    public GenericResponseDTO<List<BillResDTO>> listAll() {
        return GenericResponseDTO.<List<BillResDTO>>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .data(billService.getAllBills())
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("get-by-status/{status}")
    public GenericResponseDTO<List<BillResDTO>> listByStatus(@PathVariable String status) {
        return GenericResponseDTO.<List<BillResDTO>>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .data(billService.getBillsByStatus(status))
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping(value = "/create/", consumes = "application/json")
    public GenericResponseDTO<BillResDTO> createBill(@RequestBody BillReqDTO billReqDTO) {
        return GenericResponseDTO.<BillResDTO>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .data(billService.saveBill(billReqDTO))
                .code(HttpStatus.OK.value())
                .build();
    }
}
