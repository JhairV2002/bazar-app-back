package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.BillDetailReqDTO;
import jv.bazar.amacame.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailervice {
    @Autowired
    private ProductMapper productMapper;

    public boolean validateProducts(BillDetailReqDTO billDetailLines) {
        return billDetailLines.getBillDetailLines().stream().allMatch(billDetailLine ->
                productMapper.productReqDTOToProduct(billDetailLine.getProduct()).getProductStock() >= billDetailLine.getQuantity() &&
                productMapper.productReqDTOToProduct(billDetailLine.getProduct()).getIsActive()
        );
    }
}
