import shared
import SwiftUI

struct CurrencyRate: Identifiable {
    let id: UUID
    let iconUrl: String
    let title: String
    var amountStr: String
}

struct CurrencyRow: View {
    let iconUrl: String
    let title: String
    @Binding var amountStr: String

    var body: some View {
        HStack {
//            Image(systemName: iconUrl)
//                .frame(width: 24)
            Text(title)
                .frame(minWidth: 80, alignment: .leading)
            TextField("Enter value", text: $amountStr)
                .textFieldStyle(RoundedBorderTextFieldStyle())
        }
        .padding(.vertical, 4)
    }
}

struct CurrencyListView: View {
    @State private var items: [CurrencyRate] = [
        CurrencyRate(id: UUID(), iconUrl: "1.png", title: "Name", amountStr: ""),
        CurrencyRate(id: UUID(), iconUrl: "2.png", title: "Email", amountStr: ""),
        CurrencyRate(id: UUID(), iconUrl: "3.png", title: "Phone", amountStr: "")
    ]

    var body: some View {
        List {
            ForEach($items) { $item in
                CurrencyRow(iconUrl: item.iconUrl, title: item.title, amountStr: $item.amountStr)
            }
        }
        .listStyle(PlainListStyle())
    }
}

struct CurrencyListView_Previews: PreviewProvider {
    static var previews: some View {
        CurrencyListView()
            .previewLayout(.sizeThatFits)
    }
}
