package com.stackroute.muzicx.controllers;

import com.stackroute.muzicx.TrackDTO.Trackdto;
import com.stackroute.muzicx.domain.Track;
import com.stackroute.muzicx.exception.TrackAlreadyExistsException;
import com.stackroute.muzicx.exception.TrackNotFoundException;
import com.stackroute.muzicx.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/v1")
@ControllerAdvice(basePackages="com.stackroute.muzicx")
@Slf4j
public class TrackController {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    TrackService trackService;
    @Autowired
    ModelMapper modelMapper;

    @Value("${exceptionMsg}")
    String exp;

    @Value("${Savesuccessmsg}")
    String success;
    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }


    @ExceptionHandler(TrackAlreadyExistsException.class)
    @PostMapping("track")
    @CrossOrigin
    public ResponseEntity<?> saveTrack(@RequestBody Trackdto trackdto) throws InterruptedException {
        ResponseEntity responseEntity;

        try{

            responseEntity = new ResponseEntity<Track>(trackService.saveTrack(modelMapper.map(trackdto,Track.class)), HttpStatus.CREATED);


        }catch(TrackAlreadyExistsException e){
           responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            logger.error("This is an error message");
            e.printStackTrace();
        }
       return  responseEntity;

    }

    @GetMapping("track")
    @CrossOrigin
    public  ResponseEntity<?> getallTracks(){
        ResponseEntity responseEntity;
            try {
                responseEntity = new ResponseEntity <List<Track>>(trackService.getAllTracks(),HttpStatus.OK);
            } catch (TrackNotFoundException e) {
                responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

                e.printStackTrace();
            }
        return  responseEntity;
    }

//    @GetMapping("trackk")
//    public  ResponseEntity<?> getTopTracks(){
//
//        return trackService.getTopTracks();
//    }

    @DeleteMapping("track/{id}")
    @CrossOrigin
    public 	ResponseEntity<?> delete(@PathVariable(value = "id") long id){

        ResponseEntity responseEntity;

        try{
           int result =  trackService.deleteTrack(id);
            System.out.println(result);
            if(result == 1){
                responseEntity = new ResponseEntity<String>("Succesfully deleted", HttpStatus.CREATED);

            }else{
                responseEntity = new ResponseEntity<String>(exp, HttpStatus.CONFLICT);

            }


        }catch(Exception e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

        }

        return  responseEntity;

    }

    @ExceptionHandler(TrackNotFoundException.class)
    @PutMapping("track/{id}")
    @CrossOrigin
    public ResponseEntity<?> updateTrack(@PathVariable(value = "id") int id,Track track){

        ResponseEntity responseEntity;

        try{
            trackService.UpdateTrack(id,track);
            responseEntity = new ResponseEntity<String>("Succesfully updated", HttpStatus.CREATED);


        }catch(TrackNotFoundException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

        }
        return  responseEntity;

    }


    @GetMapping("track/{id}")
    @CrossOrigin
    //handler to get a track by id
    public ResponseEntity<?> getTrack(@PathVariable String id) {
        try {
            return new ResponseEntity<>(trackService.getTrackById(Integer.parseInt(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
