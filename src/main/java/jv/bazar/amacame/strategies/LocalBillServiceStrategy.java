package jv.bazar.amacame.strategies;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.factory.BillPromoFactory;
import jv.bazar.amacame.factory.ProductPromoFactory;
import jv.bazar.amacame.interfaces.IBillService;
import jv.bazar.amacame.mappers.BillMapper;
import jv.bazar.amacame.repositories.BillRepository;
import jv.bazar.amacame.services.BillDetailLineService;
import jv.bazar.amacame.services.ProductService;

import java.util.List;

public class LocalBillServiceStrategy implements IBillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final ProductService productService;

    private final BillPromoFactory billPromoFactory;

    private final ProductPromoFactory productPromoFactory;

    public LocalBillServiceStrategy(BillRepository billRepository, BillMapper billMapper, ProductService productService, BillPromoFactory billPromoFactory, ProductPromoFactory productPromoFactory) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
        this.productService = productService;
        this.billPromoFactory = billPromoFactory;
        this.productPromoFactory = productPromoFactory;
    }
    @Override
    public List<BillResDTO> getBills() {
        return null;
    }

    @Override
    public BillResDTO createBill(BillReqDTO bill) {
        return null;
    }

    @Override
    public BillResDTO getBill(Long id) {
        return null;
    }
}
