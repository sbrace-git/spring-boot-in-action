@RestController
class HelloController {

    @RequestMapping("/hello")
    def hello() {
        "Hello World!"
    }
}