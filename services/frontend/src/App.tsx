import React, { useState, useEffect } from 'react';
import './index.css';

interface SimulatorStatus {
  running: boolean;
  uptime: number;
}

function App() {
  const [status, setStatus] = useState<SimulatorStatus | null>(null);

  useEffect(() => {
    // Mocking an API call to /v1/status
    setStatus({ running: true, uptime: 120 });
  }, []);

  return (
    <div className="min-h-screen bg-background p-8 font-sans">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex items-center justify-between border-b border-border pb-6">
          <h1 className="text-3xl font-bold tracking-tight text-primary">Message Simulator</h1>
          <div className={`inline-flex items-center rounded-full border px-3 py-1 text-sm font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 ${
            status?.running 
              ? 'border-transparent bg-emerald-500/20 text-emerald-500 hover:bg-emerald-500/30' 
              : 'border-transparent bg-destructive/20 text-destructive hover:bg-destructive/30'
          }`}>
            {status?.running ? 'RUNNING' : 'STOPPED'}
          </div>
        </header>
        
        <main className="grid gap-6 md:grid-cols-3">
          <section className="rounded-xl border bg-card text-card-foreground shadow col-span-1 p-6">
            <h2 className="text-lg font-semibold border-b pb-2 mb-4">Overview</h2>
            <p className="text-sm text-muted-foreground mb-6">Uptime: {status?.uptime || 0} seconds</p>
            <div className="flex flex-col space-y-2">
              <button 
                className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2"
                onClick={() => setStatus({running: true, uptime: 0})}
              >
                Start Simulator
              </button>
              <button 
                className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-destructive hover:text-destructive-foreground h-10 px-4 py-2"
                onClick={() => setStatus({running: false, uptime: 0})}
              >
                Stop Simulator
              </button>
            </div>
          </section>
          
          <section className="rounded-xl border bg-card text-card-foreground shadow md:col-span-2 p-6">
            <h2 className="text-lg font-semibold border-b pb-2 mb-4">Recent Transactions</h2>
            <div className="space-y-4">
              <div className="flex items-center justify-between text-xs uppercase text-muted-foreground font-medium pb-2 border-b">
                <div className="flex-1">Time</div>
                <div className="flex-1">Protocol</div>
                <div className="flex-1">Status</div>
              </div>
              <div className="flex items-center justify-between text-sm py-1 border-b border-muted">
                <div className="flex-1">10:32 AM</div>
                <div className="flex-1">SOAP</div>
                <div className="flex-1 font-medium text-emerald-500">200 OK</div>
              </div>
              <div className="flex items-center justify-between text-sm py-1 border-b border-muted">
                <div className="flex-1">10:33 AM</div>
                <div className="flex-1">JMS</div>
                <div className="flex-1 font-medium text-emerald-500">ACK</div>
              </div>
            </div>
          </section>
        </main>
      </div>
    </div>
  );
}

export default App;
