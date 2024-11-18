package jv.bazar.amacame.interfaces;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;

import java.util.List;

public interface IBillService {
    List<BillResDTO> getBills();
    BillResDTO createBill(BillReqDTO bill);
    BillResDTO getBill(Long id);
}
