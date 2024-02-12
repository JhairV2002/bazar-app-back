package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.entity.Bill;
import jv.bazar.amacame.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/get-all/")
    public List<BillResDTO> listAll() {
        return billService.getAllBills();
    }

    @PostMapping(value = "/create/", consumes = "application/json")
    public BillResDTO createBill(@RequestBody BillReqDTO billReqDTO) {
        return billService.saveBill(billReqDTO);
    }
}
