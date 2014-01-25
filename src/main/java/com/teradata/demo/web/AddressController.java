package com.teradata.demo.web;

import com.teradata.demo.entity.Address;
import com.teradata.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地区
 *
 * Created by zhu on 14-1-25.
 */
@Controller
public class AddressController {
    private AddressService addressService;

    @RequestMapping(value = "/address/list", method = RequestMethod.GET)
    public @ResponseBody List<Address> query(@RequestParam("term") String term) {
        return addressService.search(term);
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
}
