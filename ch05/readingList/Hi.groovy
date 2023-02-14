@RestController
class Hi {
	@RequestMapping("/hi")
	def hi() {
		"hiya"
	}
}