import shared
import SwiftUI

struct CurrencyRatesViewData : Identifiable {
    let id: UUID
    let iconUrl: String
    let title: String
    let amountStr: String
}

struct CurrencyRow: View {
    let iconUrl: String
    let title: String
    @Binding var amountStr: String

    var body: some View {
        HStack {
            Image(systemName: "pencil")
                .frame(width: 24)
            
            Text(title)
                .frame(minWidth: 80, alignment: .leading)
            
            Spacer()
            
            TextField("Enter value", text: $amountStr)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .frame(maxWidth: 70)
        }
        .padding(.vertical, 4)
    }
}

class SampleViewModelWrapper: ObservableObject {
    private let viewModel: ConverterViewModel

//    private var cancellables = Set<AnyCancellable>()

    @Published var rates: [String] = []

    init() {
        let networkClient = RatesNetworkClient()
        let repository = RatesRepository(ratesNetworkClient: networkClient)
        let interactor = ConverterInteractor(ratesRepository: repository)
        self.viewModel = ConverterViewModel(interactor: interactor)
        startObserving()
    }

    private func startObserving() {
        viewModel.startRatesLoading()
        viewModel.observeData(onEach: { value in
            self.rates = value.rates.map { $0.code }
        })
    }
}

struct CurrencyListView: View {
//    @State private var items: [CurrencyRate] = [
//        CurrencyRate(id: UUID(), iconUrl: "1.png", title: "RUB", amountStr: "100.0"),
//        CurrencyRate(id: UUID(), iconUrl: "2.png", title: "EUR", amountStr: "1"),
//        CurrencyRate(id: UUID(), iconUrl: "3.png", title: "USD", amountStr: "0.85"),
//        CurrencyRate(id: UUID(), iconUrl: "3.png", title: "JPY", amountStr: "149.40"),
//    ]
    @StateObject private var viewModelWrapper = SampleViewModelWrapper()

    var body: some View {
        List {
            ForEach(viewModelWrapper.rates, id: \.self) { item in
                CurrencyRow(iconUrl: "", title: item, amountStr: .constant("10.0"))
            }
        }
        .listStyle(PlainListStyle())
    }
}

#Preview {
    CurrencyListView()
}
