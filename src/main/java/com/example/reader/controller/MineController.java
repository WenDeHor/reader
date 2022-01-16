package com.example.reader.controller;

import com.example.reader.model.BaseReserve;
import com.example.reader.service.ReserveAssetsService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class MineController {
    private ReserveAssetsService service;
    @GetMapping
    public List<BaseReserve> getAll() throws IOException, ParseException {
        return service.getBaseReserveList();
    }
}
