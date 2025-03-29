import shared
import SwiftUI

struct EditableItem: Identifiable {
    let id: UUID
    let iconName: String
    let title: String
    var value: String
}

struct EditableItemRow: View {
    let iconName: String
    let title: String
    @Binding var value: String

    var body: some View {
        HStack {
            Image(systemName: iconName)
                .frame(width: 24)
            Text(title)
                .frame(minWidth: 80, alignment: .leading)
            TextField("Enter value", text: $value)
                .textFieldStyle(RoundedBorderTextFieldStyle())
        }
        .padding(.vertical, 4)
    }
}

struct EditableListView: View {
    @State private var items: [EditableItem] = [
        EditableItem(id: UUID(), iconName: "pencil", title: "Name", value: ""),
        EditableItem(id: UUID(), iconName: "envelope", title: "Email", value: ""),
        EditableItem(id: UUID(), iconName: "phone", title: "Phone", value: "")
    ]

    var body: some View {
        List {
            ForEach($items) { $item in
                EditableItemRow(iconName: item.iconName, title: item.title, value: $item.value)
            }
        }
        .listStyle(PlainListStyle())
    }
}

//struct ContentView: View {
//    @State private var firstNum: String = "0"
//    @State private var secondNum: String = "0"
//    private var sum: String {
//        return "🤔"
//    }
//    
//    var body: some View {
//        VStack(alignment: .center) {
//            Text("Hello")
//            HStack(alignment: .center) {
//                TextField("A", text: $firstNum)
//                    .keyboardType(.numberPad)
//                    .multilineTextAlignment(.center)
//                    .frame(width: 30)
//                Text("+")
//                TextField("B", text: $secondNum)
//                    .keyboardType(.numberPad)
//                    .multilineTextAlignment(.center)
//                    .frame(width: 30)
//                Text("=")
//                Text(sum)
//            }
//        }
//    }
//}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}


struct EditableListView_Previews: PreviewProvider {
    static var previews: some View {
        EditableListView()
            .previewLayout(.sizeThatFits)
    }
}
