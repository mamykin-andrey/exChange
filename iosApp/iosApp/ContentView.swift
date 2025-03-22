import Shared
import SwiftUI

struct ContentView: View {
    @State private var firstNum: String = "0"
    @State private var secondNum: String = "0"
    private var sum: String {
        return "🤔"
    }
    
    var body: some View {
        VStack(alignment: .center) {
            Text("Hello")
            HStack(alignment: .center) {
                TextField("A", text: $firstNum)
                    .keyboardType(.numberPad)
                    .multilineTextAlignment(.center)
                    .frame(width: 30)
                Text("+")
                TextField("B", text: $secondNum)
                    .keyboardType(.numberPad)
                    .multilineTextAlignment(.center)
                    .frame(width: 30)
                Text("=")
                Text(sum)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
