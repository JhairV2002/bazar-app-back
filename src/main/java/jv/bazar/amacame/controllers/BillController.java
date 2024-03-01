package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.entity.Bill;
import jv.bazar.amacame.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@CrossOrigin(origins = "http://localhost:3000")

public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/get-all/")
    public List<BillResDTO> listAll() {
        return billService.getAllBills();
    }

    @GetMapping("get-by-status/{status}")
    public List<BillResDTO> listByStatus(@PathVariable String status) {
        return billService.getBillsByStatus(status);
    }

    @PostMapping(value = "/create/", consumes = "application/json")
    public BillResDTO createBill(@RequestBody BillReqDTO billReqDTO) {
        return billService.saveBill(billReqDTO);
    }
}
